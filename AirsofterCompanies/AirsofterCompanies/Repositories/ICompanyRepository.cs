using AirsofterCompanies.Models;
using System;
using System.Threading.Tasks;

namespace AirsofterCompanies.Repositories
{
    internal interface ICompanyRepository
    {
        Task<string> Login(string username, string password);
        Task<CompanyAccountModel> GetById(Guid id);
        Task<CompanyAccountModel> GetByUsername(string username);
    }
}
