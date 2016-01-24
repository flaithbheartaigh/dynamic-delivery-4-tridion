When you request a page through DD4T, it will be rendered automatically with a view which corresponds with the page template used on the page. For example: if you use a page template called 'Standard', the application will look for a view with the same name.
Note that the name does NOT include the extension .cshtml (or .aspx). So if the name is set to 'Standard', the system looks for the view 'Standard.cshtml'.

It's also possible to override this default behaviour by adding a metadata field 'view' to the page template. In this field (which should be a plain text field), you can enter a different name for your view.

# View locations #

MVC looks for a view in multiple locations:
  * /Views/ControllerName/ViewName.aspx
  * /Views/ControllerName/ViewName.cshtml
  * /Views/Shared/ViewName.aspx
  * /Views/Shared/ViewName.cshtml

If you are working with the 'quick start' solution as created by the DD4T Visual Studio template, the pages will be served by the PageController. In that case, the application will look for the views in the folder /Views/Page.

# Razor #

We recommend to use Razor views (.cshtml) rather than aspx. Razor is simple to understand and prettier to read. Also, it is much more common in combination with MVC. Our examples will be based on Razor.

# Model #

Page views are strongly typed: they use the DD4T.ContentModel.IPage class for a model. A simple example demonstrates this:

```
{code}
@model DD4T.ContentModel.IPage
<h1>@Model.Title</h1>
{code}		
```

The first line (with the lower case 'm') tells the view that the model is of the type 'DD4T.ContentModel.IPage'. The second line uses the page object to write out its 'Title' property. Note the upper case 'M' when reading from the model.


# Layouts and Sections #
In Razor you can define layouts. A layout is comparable to a master page in ASP.Net web forms programming.

A layout always contains a body. The body is simply the output of the 'regular view' (not the layout), which is inserted into the layout in place of the @RenderBody() call.
Besides the body, the layout can contain one or more 'sections'. A section is a named block of 'view code' that is defined in the regular view using the @section{ } construction. The section is inserted on the position in the layout indicated by the @RenderSection('..') call.

Example of a regular (page) view:

```
@{
    Layout = "~/Views/Shared/_CommonLayout.cshtml";
}
This is the body of the view.
@section Header
{
  This is a section called 'Header'
}
```


The corresponding layout (_CommonLayout.cshtml) could look like this:_

```
<html>
<body>
<div>this is some HTML defined by the layout</div>
<header>
@RenderSection('Header')
</header>
@RenderBody
</body>
</html>
```

Note that layouts can be bound to the IPage class using the @model directive. This allows you to write out information from the page in the layout, just like you would from the page view.


---

One of the first things you would probably want to do in your page view, is show some content on the page. This is described in the next section, [Rendering component presentations](DotNetComponentPresentations.md).


