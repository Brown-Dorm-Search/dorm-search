/** @jsx React.DOM */
var makes = [
  {id: 1, name: 'Toyota'},
  {id: 2, name: 'Honda'},
  {id: 3, name: 'Ford'}
]
var models = [
  {id: 1, makeId: 1, name: 'Camry'},
  {id: 2, makeId: 1, name: 'Tundra'},
  {id: 3, makeId: 1, name: 'Tacoma'},
  {id: 4, makeId: 2, name: 'Civic'},
  {id: 5, makeId: 2, name: 'Accord'},
  {id: 6, makeId: 2, name: 'CR-V'},
  {id: 7, makeId: 3, name: 'Escape'},
  {id: 8, makeId: 3, name: 'Focus'},
  {id: 9, makeId: 3, name: 'Mustang'}
]
var Chained = React.createClass({
  getDefaultProps: function() {
    return {
      makes: [],
      models: []
    }
  },
  getInitialState: function() {
    return {makeSelections: {}}
  },
  handleMakeChange: function(selections) {
    this.setState({ makeSelections: selections })
  },
  handleModelChange: function(selections) {
    // Do something with the selection info
    // selections is an object where the keys are item ids and the values
    // are true or false, indicating the selection status
  },
  render: function() {
    var selectedModels = this.props.models.filter(
      // Filter so only selected items are displayed
      function(model) { return this.state.makeSelections[model.makeId] }.bind(this)
    )
    return (
      <div>
        <MultiSelect items={this.props.makes} onChange={this.handleMakeChange} />
        <MultiSelect items={selectedModels} onChange={this.handleModelChange} />
      </div>
    )
  }
})
React.renderComponent(<Chained makes={makes} models={models} />, document.getElementById('multiselect-chained'))
