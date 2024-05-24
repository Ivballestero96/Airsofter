using AirsofterCompanies.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace AirsofterCompanies.Repositories
{
    public interface IFieldRepository
    {
        Task<List<Field>> GetFieldsWithIdAndNameAsync(Guid companyId);
    }
}