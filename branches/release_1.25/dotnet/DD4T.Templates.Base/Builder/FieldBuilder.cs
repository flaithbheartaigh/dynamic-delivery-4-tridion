﻿using System;
using System.Collections.Generic;
using System.Text;
using Dynamic = DD4T.ContentModel;
using TCM = Tridion.ContentManager.ContentManagement;
using Tridion.ContentManager.ContentManagement.Fields;
using DD4T.ContentModel.Exceptions;
using DD4T.ContentModel;
using Tridion.Logging;
using Tridion.ContentManager.Templating;
using DD4T.Templates.Base.Utils;

namespace DD4T.Templates.Base.Builder
{
    public class FieldBuilder
    {
        protected static TemplatingLogger log = TemplatingLogger.GetLogger(typeof(FieldBuilder));
        public static Dynamic.Field BuildField(TCM.Fields.ItemField tcmItemField, int linkLevels, bool resolveWidthAndHeight, BuildManager manager)
        {
            Dynamic.Field f = new Dynamic.Field();

            if (tcmItemField == null)
            {
                GeneralUtils.TimedLog("item field is null");
                throw new FieldHasNoValueException();
            }
            f.Name = tcmItemField.Name;
            if (tcmItemField is TCM.Fields.XhtmlField)
            {
                TCM.Fields.XhtmlField sField = (TCM.Fields.XhtmlField)tcmItemField;
                GeneralUtils.TimedLog(string.Format("item field {0} has {1} values", tcmItemField.Name, sField.Values.Count));
                if (sField.Values.Count == 0)
                {
                    throw new FieldHasNoValueException();
                }
                foreach (string v in sField.Values)
                {
                    f.Values.Add(v);
                }
                f.FieldType = FieldType.Xhtml;
                return f;
            }
            if (tcmItemField is TCM.Fields.MultiLineTextField)
            {
                TCM.Fields.TextField sField = (TCM.Fields.MultiLineTextField)tcmItemField;
                GeneralUtils.TimedLog(string.Format("item field {0} has {1} values", tcmItemField.Name, sField.Values.Count));
                if (sField.Values.Count == 0)
                    throw new FieldHasNoValueException();
                foreach (string v in sField.Values)
                {
                    f.Values.Add(v);
                }
                f.FieldType = FieldType.MultiLineText;
                return f;
            }
            if (tcmItemField is TCM.Fields.TextField)
            {
                TCM.Fields.TextField sField = (TCM.Fields.TextField)tcmItemField;
                GeneralUtils.TimedLog(string.Format("item field {0} has {1} values", tcmItemField.Name, sField.Values.Count));
                if (sField.Values.Count == 0)
                    throw new FieldHasNoValueException();
                foreach (string v in sField.Values)
                {
                    f.Values.Add(v);
                }
                f.FieldType = FieldType.Text;
                return f;
            }
            if (tcmItemField is TCM.Fields.KeywordField)
            {
                TCM.Fields.KeywordField sField = (TCM.Fields.KeywordField)tcmItemField;
                GeneralUtils.TimedLog(string.Format("item field {0} has {1} values", tcmItemField.Name, sField.Values.Count));
                if (sField.Values.Count == 0)
                    throw new FieldHasNoValueException();

                // add keyword values
                f.KeywordValues = new List<Keyword>();
                // we will wrap each linked component in a ContentModel component
                f.Values = new List<string>();
                foreach (TCM.Keyword kw in sField.Values)
                {
                    // todo: add binary to package, and add BinaryUrl property to the component
                    f.Values.Add(kw.Title);
                    f.KeywordValues.Add(manager.BuildKeyword(kw));
                }
                f.FieldType = FieldType.Keyword;
                KeywordFieldDefinition fieldDef = (KeywordFieldDefinition)sField.Definition;
                f.CategoryId = fieldDef.Category.Id;
                f.CategoryName = fieldDef.Category.Title;
                return f;
            }
            if (tcmItemField is TCM.Fields.NumberField)
            {
                TCM.Fields.NumberField sField = (TCM.Fields.NumberField)tcmItemField;
                GeneralUtils.TimedLog(string.Format("item field {0} has {1} values", tcmItemField.Name, sField.Values.Count));
                if (sField.Values.Count == 0)
                    throw new FieldHasNoValueException();
                f.NumericValues = (List<double>)sField.Values;
                f.Values = new List<string>();
                foreach (double d in f.NumericValues)
                {
                    f.Values.Add(Convert.ToString(d));
                }
                f.FieldType = FieldType.Number;
                return f;
            }
            if (tcmItemField is TCM.Fields.DateField)
            {
                TCM.Fields.DateField sField = (TCM.Fields.DateField)tcmItemField;
                GeneralUtils.TimedLog(string.Format("item field {0} has {1} values", tcmItemField.Name, sField.Values.Count));
                if (sField.Values.Count == 0)
                    throw new FieldHasNoValueException();
                f.DateTimeValues = (List<DateTime>)sField.Values;
                f.Values = new List<string>();
                foreach (DateTime dt in f.DateTimeValues)
                {
                    f.Values.Add(Convert.ToString(dt));
                }
                f.FieldType = FieldType.Date;
                return f;
            }
            if (tcmItemField is TCM.Fields.MultimediaLinkField)
            {
                TCM.Fields.MultimediaLinkField sField = (TCM.Fields.MultimediaLinkField)tcmItemField;
                GeneralUtils.TimedLog(string.Format("item field {0} has {1} values", tcmItemField.Name, sField.Values.Count));
                if (sField.Values.Count == 0)
                    throw new FieldHasNoValueException();

                // we will wrap each linked component in a ContentModel component
                f.LinkedComponentValues = new List<Dynamic.Component>();
                foreach (TCM.Component comp in sField.Values)
                {
                    // todo: add binary to package, and add BinaryUrl property to the component
                    f.LinkedComponentValues.Add(manager.BuildComponent(comp, linkLevels - 1, resolveWidthAndHeight));
                }
                f.Values = new List<string>();
                foreach (Dynamic.Component c in f.LinkedComponentValues)
                {
                    f.Values.Add(c.Id);
                }
                f.FieldType = FieldType.MultiMediaLink;
                return f;
            }
            if (tcmItemField is TCM.Fields.ComponentLinkField)
            {
                TCM.Fields.ComponentLinkField sField = (TCM.Fields.ComponentLinkField)tcmItemField;
                GeneralUtils.TimedLog(string.Format("item field {0} has {1} values", tcmItemField.Name, sField.Values.Count));
                if (sField.Values.Count == 0)
                    throw new FieldHasNoValueException();
                // we will wrap each linked component in a ContentModel component
                f.LinkedComponentValues = new List<Dynamic.Component>();
                foreach (TCM.Component comp in sField.Values)
                {
                    f.LinkedComponentValues.Add(manager.BuildComponent(comp, linkLevels - 1, resolveWidthAndHeight));
                }
                f.Values = new List<string>();
                foreach (Dynamic.Component c in f.LinkedComponentValues)
                {
                    f.Values.Add(c.Id);
                }
                f.FieldType = FieldType.ComponentLink;
                return f;
            }

            if (tcmItemField is TCM.Fields.EmbeddedSchemaField)
            {
                TCM.Fields.EmbeddedSchemaField sField = (TCM.Fields.EmbeddedSchemaField)tcmItemField;
                GeneralUtils.TimedLog(string.Format("item field {0} has {1} values", tcmItemField.Name, sField.Values.Count));
                if (sField.Values.Count == 0)
                    throw new FieldHasNoValueException();
                // we will wrap each linked component in a ContentModel component
                f.EmbeddedValues = new List<Dynamic.FieldSet>();
                f.EmbeddedSchema = manager.BuildSchema(((EmbeddedSchemaFieldDefinition)sField.Definition).EmbeddedSchema);
                foreach (TCM.Fields.ItemFields embeddedFields in sField.Values)
                {
                    f.EmbeddedValues.Add(manager.BuildFields(embeddedFields, linkLevels, resolveWidthAndHeight));
                }
                f.FieldType = FieldType.Embedded;
                return f;
            }

            throw new FieldTypeNotDefinedException();
        }
    }
}