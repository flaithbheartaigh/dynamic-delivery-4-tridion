Each page template in Tridion has a metadata field 'view', which contains the name of a view. Note that the name does NOT include the extension .cshtml (or .aspx). If the name is set to 'standard', the system looks for the view 'standard.cshtml'.

# View locations #

MVC looks for a view in multiple locations:
**/Views/ControllerName/ViewName.aspx** /Views/ControllerName/ViewName.cshtml
**/Views/Shared/ViewName.aspx** /Views/Shared/ViewName.cshtml

We recommend to create a PageController and a ComponentController in each web application. Both classes must subclass TridionControllerBase from the DD4T framework. That way, the views can be nicely organized as follows:

**Views that are associated with pages are stored in /Views/Page** Views that are associated with component presentations are stored in /Views/Component
**Views that are used by other views are stored in /Views/Shared**

It is always possible to write your own ViewEngine to organize the views differently.


# Razor #

We recommend to use Razor views (.cshtml) rather than aspx. Razor is simple to understand and prettier to read. Also, it is much more common in combination with MVC. Our examples will be based on Razor.

# Model #

Page views are strongly typed: they use the DD4T.ContentModel.Page class for a model. A simple example demonstrates this:

{code}
@model DD4T.ContentModel.Page
&lt;h1&gt;@Model.Title&lt;/h1&gt;
{code}

The first line (with the lower case 'm') tells the view that the model is of the type 'DD4T.ContentModel.Page'. The second line uses the page object to write out its 'Title' property. Note the upper case 'M' when reading from the model.


# Layouts and Sections #
In Razor you can define layouts. A layout is comparable to a master page in ASP.Net web forms programming.

A layout always contains a body. The body is simply the output of the 'regular view' (not the layout), which is inserted into the layout in place of the @RenderBody() call.
Besides the body, the layout can contain one or more 'sections'. A section is a named block of 'view code' that is defined in the regular view using the @section{ } construction. The section is inserted on the position in the layout indicated by the @RenderSection('..') call.

Example of a regular (page) view:

> @{
> > Layout = "~/Views/Shared/_CommonLayout.cshtml";
}
This is thebody  of the view.
@section Header
{

> This is a section called 'Header'
}_



The corresponding layout (_CommonLayout.cshtml) could look like this:_


> &lt;html&gt;
&lt;body&gt;
&lt;div&gt;this is some HTML defined by the layout&lt;/div&gt;
&lt;header&gt;
@RenderSection('Header')
&lt;/header&gt;
@RenderBody
&lt;/body&gt;
&lt;/html&gt;

Note that layouts can be bound to the IPage class using the @model directive. This allows you to write out information from the page in the layout, just like you would from the page view.


# Component presentations #

Somewhere on the page you will want to show the component presentations. This can be very straightforward, if you are showing all component presentations in the same location on the page. But you may want to have more subtle logic, e.g. showing some component presentations on the left hand side and some on the right hand side.

The DD4T framework offers a number of helper functions to help you achieve this.

RenderComponentPresentations
Example: @Html.RenderComponentPresentations()
Shows all the embedded component presentations on the page.

RenderComponentPresentationsByView(string viewName)
Example: @Html.RenderComponentPresentationsByView("Caroussel")
Shows component presentations whose component template is associated with a view that starts with 'Caroussel'. Note: it is recommended to group component templates based on the first part of their view name, so similar component presentations can easily be shown in one location on the page.

RenderComponentPresentationsBySchema(string schemaName)
Example: @Html.RenderComponentPresentationsBySchema("Header")
Shows component presentations whose component is based on a schema with a title starting with 'Header'.

Reversing the logic
You can start the schema name with an exclamation mark, to show all CPs whose schema does NOT have a title starting with 'Header'.


There are more overloads, but they are self-explanatory.


