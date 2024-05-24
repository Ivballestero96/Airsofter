using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AirsofterCompanies.Models
{
    public class CompanyAccountModel
    {
        public Guid Id { get; set; }
        public string Username { get; set; }
        public string CompanyName { get; set; }
        public string Email { get; set; }
    }

}
