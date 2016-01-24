When you render a component presentation in a [page view](DotNetPageViews.md), you are telling the application to take a component that sits on the page, and render it with a view. The name of this view corresponds with the title of the component template in Tridion, just like the name of the page view corresponds with the title of the page template. It is also possible to override the view name by adding a metadata field called 'view' to the component template.

The component view must be placed in the folder /Views/Component (at least, if you have stuck with the ComponentController which the Visual Studio template gives you).

# The basics #

Component views are very similar to Page Views. The main difference is the model: Component Views work on IComponents while Page Views work on IPages. So the first line in your component view is:

```
@model DD4T.ContentModel.IComponent
&lt;h1&gt;@Model.Title&lt;/h1&gt;
```


The most important task of the component view is to write out the fields of the component. Take a look at the following code:

```
@Model.Fields["heading"].Value
```

This is how you write out the field 'heading'.  Or rather, the first value of this field, since fields in Tridion can have multiple values.


# Field names #

In the code example above, the word 'heading' is contained within quotes. The names of the fields that you are writing out are not modeled in DD4T. Intellisense does not help you here, you have to find out the names  of the fields in your component yourself. There are three ways to go about:

  1. Open the component in Tridion and click on the Source tab. The field name is the name of the XML element that contains the field value (e.g. `<heading>`Some text`</heading>`). However, you cannot use this approach for metadata fields, only for content fields.
  1. Open the schema in Tridion and look for the XML name of the field.

# Field types #

In DD4T, each field has a number of 'value collections':
  * Field.Values contains a List of strings, representing the text value. This is used for single and multi line text fields and rich text fields.
  * Field.NumericValues contains a List of integers, representing the numeric value. This is used number fields.
  * Field.DateTimeValues contains a List of DateTimes. This is used date fields.
  * Field.LinkedComponentValues contains a List of IComponents. Used for component links and multimedia links.
  * Field.EmbeddedValues contains a List of IFieldSets. Used for embedded fields.
  * Field.Keywords contains a List of IKeywords. Used for keyword fields.

Notes:
  1. The property 'Value' is a shortcut to the first value of the Values collection.
  1. For numeric fields, a string representation of the numeric values is available in the Values collection.
  1. For date fields, a string representation of the date/time values is available in the Values collection.
  1. For component link and multimedia link fields, the URI of the linked components is available in the Values collection.
  1. For keyword fields, the title of the keyword is available in the Values collection.


# Rich text fields #

For rich text fields we have provided an extension method for System.String, called ResolveRichText(). If you call this method, the links inside the rich text field are resolved, and the 'xhtml' and 'xlink' namespace references are removed. Use this wherever you need to write out rich text.

```
<div class="bodytext">
@Model.Fields["body"].ResolveRichText()
</div>
```

# Images and documents #

Images and binary documents (PDF, Word, etc) have a Multimedia property which gives access to the URL. Their use is demonstrated in this code:

```
<img src="@Model.Fields["image"].LinkedComponentValues[0].Multimedia.Url" 
	alt="@Model.Fields["image"].LinkedComponentValues[0].Title"/><br/>
<a href="@Model.Fields["document"].LinkedComponentValues[0].Multimedia.Url" 
	title="@Model.Fields["image"].LinkedComponentValues[0].Title"/>
	Click to download the document
</a>
```

Note that the multimedia components that contain these images / documents are not usually put on the page directly. Most of the times, you must follow a (multimedia) link first, as shown in the code example.

<a href='Hidden comment: 
When you click on the link, you may not actually see the image. This is the case if you are publishing the images to the broker database, just like the pages and component presentations.
TODO: create some info about serving binaries from the broker database!!
'></a>