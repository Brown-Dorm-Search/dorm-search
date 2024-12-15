Please get in touch with me if you have any issues or suggestions for this project.  It's under active development
and I'm taking requests for new features or bug fixes. You can contact me via e-mail at
[james@neodon.com](mailto:james@neodon.com).

# React-MultiSelect

React-MultiSelect is a client-side [React](http://facebook.github.io/react "React") component that presents the user
with a list of items and allows them to filter and select one or more of them.

![React-MultiSelect Example](http://i.imgur.com/VLuM9W0.png "React-MultiSelect Example")

## Features

### Filters

Enter some text in the input box to filter the items listed. Selections are remembered even if the items are
momentarily not visible.

### Dynamic Data

Supports loading item lists dynamically with AJAX requests.  Selections will remain across requests as long as the item
ids stay the same. This can be helpful where you need the items in one multiselect to depend on the selections in another
multiselect and want them to update dynamically.

## Examples

Some examples are included with the project.  You can run them locally using nodejs to serve the files.

Run the following commands in the project directory, and then navigate to the URL displayed.

```
$ sudo npm install http-server -g
$ http-server
```

## Options

The options below can be passed to the React-MultiSelect component to customize its behavior.

### JSX

```
/** @jsx React.DOM */
<MultiSelect
  items={[]}
  placeholder={''}
  onChange={function(selections) {}}
  onItemSelected={function(item) {}}
  onItemDeselected={function(item) {}}
/>
```

### JavaScript

```
React.renderComponent(
  MultiSelect({
    items: {},
    placeholder: '',
    onChange: function(selections) {},
    onItemSelected: function(item) {},
    onItemDeselected: function(item) {}
  }), document.getElementById('multiselect'))
)
```

### items

Items to display in the list.  Each item must contain an id property to identify the item and a text property to
display in the item list.

### placeholder

Text to initially display in the filter input box.  Defaults to an empty string.

### onChange(selections)

Called after items are selected or deselected.  The parameter is an object where the keys are item ids and the values
are true or false to indicate whether or not an item is selected.  Use this event when you need a summary of changes
and not individual selections or deselections as they occur.

### onItemSelected(item)

Called whenever an item is selected.  The parameter is the item.  Use this when you
want to capture selections as they occur.

### onItemDeselected(item)

Corresponding event for onItemSelected that is called whenever an item is deselected.

## CSS

You can use the following CSS selectors to customize the appearance of the component.
  
```
div.multiselect
div.multiselect input // Filter text
div.multiselect ul
div.multiselect li
div.multiselect li:hover
div.multiselect button
div.multiselect .selected // Style for a selected item
div.multiselect .deselected // Style for a deselected item
```

See example/basic.css to get started with styling.

