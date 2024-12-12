/** @jsx React.DOM */
var items = [
  {id: 1, text: 'Apple'},
  {id: 2, text: 'Apricot'},
  {id: 3, text: 'Avocado'},
  {id: 4, text: 'Banana'},
  {id: 5, text: 'Breadfruit'},
  {id: 6, text: 'Bilberry'},
  {id: 7, text: 'Blackberry'},
  {id: 8, text: 'Blackcurrant'},
  {id: 9, text: 'Blueberry'},
  {id: 10, text: 'Boysenberry'},
  {id: 11, text: 'Cantaloupe'},
  {id: 12, text: 'Currant'},
  {id: 13, text: 'Cherry'},
  {id: 14, text: 'Cherimoya'},
  {id: 15, text: 'Cloudberry'},
  {id: 16, text: 'Coconut'},
  {id: 17, text: 'Cranberry'},
  {id: 18, text: 'Cucumber'},
  {id: 19, text: 'Damson'},
  {id: 20, text: 'Date'},
  {id: 21, text: 'Dragonfruit'},
  {id: 22, text: 'Durian'},
  {id: 23, text: 'Eggplant'},
  {id: 24, text: 'Elderberry'},
  {id: 25, text: 'Feijoa'},
  {id: 26, text: 'Fig'},
  {id: 27, text: 'Goji berry'},
  {id: 28, text: 'Gooseberry'},
  {id: 29, text: 'Grape'},
  {id: 30, text: 'Raisin'}
]
var Basic = React.createClass({displayName: "Basic",
  getDefaultProps: function() {
    return {items: []}
  },
  handleItemSelected: function(item) {
    console.log('Item selected: '+item.id+' - '+item.text)
  },
  handleItemDeselected: function(item) {
    console.log('Item deselected: '+item.id+' - '+item.text)
  },
  render: function() {
    return React.createElement(MultiSelect, {
      items: this.props.items, 
      onItemSelected: this.handleItemSelected, 
      onItemDeselected: this.handleItemDeselected}
    )
  }
})
React.renderComponent(React.createElement(Basic, {items: items}), document.getElementById('multiselect-basic'))

