﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DD4T.ContentModel
{
    public interface IRepositoryLocal : IItem
    {
        string Id { get; }
        string Title { get; }
        IPublication Publication { get; }
        IPublication OwningPublication { get; }
    }
}
