var webpack = require('webpack');

module.exports = require("./scalajs.webpack.config");

module.exports.module.rules = (module.exports.module.rules || []).concat([
    {
        test: /\.js$/,
        loader: 'ify-loader'
    }
]);
