const mongoose = require("mongoose");

const UserSchema = new mongoose.Schema();

const userSchema = new Schema({
  name: {
    type: String,
    required: true,
  },
  age: {
    type: Number,
    required: true,
  },
  userName: {
    type: String,
    required: true,
  },
});

const UserModel = mongoose.model("users", UserSchema);
module.exports = UserModel; // gives access to userModel outside of this file
