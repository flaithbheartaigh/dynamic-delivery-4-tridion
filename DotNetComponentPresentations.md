Somewhere on the page you will want to show the component presentations. This can be very straightforward, if you are showing all component presentations in the same location on the page. But you may want to have more subtle logic, e.g. showing some component presentations on the left hand side and some on the right hand side.

The DD4T framework offers a number of helper functions to help you achieve this.

**RenderComponentPresentations**

Shows all the embedded component presentations on the page.

Example:
```
@Html.RenderComponentPresentations()
```


**RenderComponentPresentationsByView(string viewName)**

Shows component presentations whose component template is associated with a view that starts with 'Caroussel'. Note: it is recommended to group component templates based on the first part of their view name, so similar component presentations can easily be shown in one location on the page.

Example:
```
@Html.RenderComponentPresentationsByView("Caroussel")
```


**RenderComponentPresentationsBySchema(string schemaName)**

Shows component presentations whose component is based on a schema with a title starting with 'Header'.

Example:
```
@Html.RenderComponentPresentationsBySchema("Header")
```

## Reversing the logic ##
You can start the schema name with an exclamation mark, to show all CPs whose schema does NOT have a title starting with 'Header'.

Example:
```
@Html.RenderComponentPresentationsByView("!Caroussel")
```

This shows component presentations whose component template is associated with a view that does NOT start with 'Caroussel'.


---

Now that your page view is rendering component presentations, it is time to look at the next topic, [Component Views](DotNetComponentViews.md).