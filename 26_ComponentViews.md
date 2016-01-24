# The basics #

Component views are very similar to Page Views. The main difference is the model: Component Views work on IComponents while Page Views work on IPages. The most important task of the component view, is to write out the fields of the component. Take a look at the following code:

```
@Model.Fields["heading"].Value
```

This is how you write out the field 'heading'.  Or rather, the first value of this field, since fields in Tridion can have multiple values.


# Field names #

In the code example above, the word 'heading' is contained within quotes. The names of the fields that you are writing out are not modeled in DD4T. Intellisense does not help you here, you have to find out the names  of the fields in your component yourself. There are three ways to go about:

  1. Open the component in Tridion and click on the Source tab. The field name is the name of the XML element that contains the field value (e.g. 

&lt;heading&gt;

Some text

&lt;/heading&gt;

). However, you cannot use this approach for metadata fields, only for content fields.
  1. Open the schema in Tridion and look for the XML name of the field.
  1. Request the XML of the entire page from DD4T.

The last approach is the simplest and most reliable. Just type '/Xml' before the path of the test page you're working with.

E.g.: if your test page has the URL http://myweb/test/page.html, the XML can be requested with the url http://myweb/Xml/test/page.html.

In the XML, just browse to a component presentation and look at the field names.


NOTE: for this to work we need to move the Xml action into DD4T (currently it doesn't exist there yet).


# Field types #

In DD4T, each field has a number of 'value collections':
  * Field.Values contains a List of strings, representing the text value. This is used for single and multi line text fields, rich text fields and date fields (important: if you want to use dates, you must convert the strings back to DateTime first!).
  * Field.NumericValues contains a List of integers, representing the numeric value. This is used number fields.
  * Field.LinkedComponentValues contains a List of IComponents. Used for component links and multimedia links.
  * Field.EmbeddedValues contains a List of IFieldSets. Used for embedded fields.

Notes:
  1. The property 'Value' is a shortcut to the first value of the Values collection.
  1. For numeric fields, a string representation of the numeric values is available in the Values collection.
  1. For component link and multimedia link fields, the URI of the linked components is available in the Values collection.


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
<img src="@Model.Fields["image"].LinkedComponentValues[0].Multimedia.Url" width="100%" alt="@Model.Fields["image"].LinkedComponentValues[0].Title"/><br/>
<a href="@Model.Fields["document"].LinkedComponentValues[0].Multimedia.Url" title="@Model.Fields["image"].LinkedComponentValues[0].Title"/>Click to download the document</a>
```


Note that the multimedia components that contain these images / documents are not usually put on the page directly. Most of the times, you must follow a (multimedia) link first, as shown in the code example.