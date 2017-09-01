const mongoose = require('mongoose');

let filmSchema = mongoose.Schema({
    name:{type:String,require:true,unique:true},
    genre:{type:String,require:true,unique:true},
    director:{type:String,require:true,unique:true},
    year:{type:String,require:true,unique:true}
});

let Film = mongoose.model('Film', filmSchema);

module.exports = Film;