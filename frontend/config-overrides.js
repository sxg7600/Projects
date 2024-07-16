const { override, addBabelPlugin } = require('customize-cra');

module.exports = override(
  addBabelPlugin('babel-plugin-macros'),
  addBabelPlugin('@babel/plugin-syntax-optional-chaining')
);
