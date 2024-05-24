using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AirsofterCompanies.Models
{
    public class Field
    {
        public string Id { get; set; }
        public string Name { get; set; }

        public Field()
        {
        }

        public Field(string id, string name )
        {
            Id = id;
            Name = name;
        }
    }
}
