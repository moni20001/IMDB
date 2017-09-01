const Film = require('../models/Film');

module.exports = {
	index: (req, res) => {
        Film.find({}).then(films => {
            res.render('film/index', {'films': films});
        })
	},
	createGet: (req, res) => {
        res.render('film/create');
	},
	createPost: (req, res) => {
        let bodyArg = req.body;
        let filmCreate = {
            name:bodyArg.name,
            genre:bodyArg.genre,
			director:bodyArg.director,
			year:bodyArg.year
        };
        Film.create(filmCreate);
        res.redirect('/')
	},
	editGet: (req, res) => {
        Film.findById(req.params.id).then(film => {
            if(!film){
                res.redirect('/');
                return;
            }
            res.render('film/edit',film);
        })
	},
	editPost: (req, res) => {
        let id = req.params.id;
        let film = req.body;
        Film.findByIdAndUpdate(id,film).then(film=>{
            res.redirect('/');
        })
	},
	deleteGet: (req, res) => {
        Film.findById(req.params.id).then(film => {
            if(!film){
                res.redirect('/');
                return;
            }
            res.render('film/delete',film);
        })
	},
	deletePost: (req, res) => {
        Film.findByIdAndRemove(req.params.id).then(film => {
            res.redirect('/');
        })

	}
};