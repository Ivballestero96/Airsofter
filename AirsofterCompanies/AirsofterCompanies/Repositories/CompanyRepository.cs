using AirsofterCompanies.API;
using AirsofterCompanies.Models;
using Newtonsoft.Json;
using System;
using System.Net.Http;
using System.Threading.Tasks;

namespace AirsofterCompanies.Repositories
{
    public class CompanyRepository : RepositoryBase, ICompanyRepository
    {
        private readonly ApiClient _apiClient;

        public CompanyRepository()
        {
            _apiClient = new ApiClient();
        }

        public async Task<string> Login(string username, string password)
        {
            return await _apiClient.Login(username, password);
        }

        public async Task<CompanyAccountModel> GetById(Guid id)
        {
            throw new NotImplementedException();
        }

        public async Task<CompanyAccountModel> GetByUsername(string username)
        {
 
            throw new Exception($"Error al obtener la compañia por nombre de usuario '{username}'");

        }
    }
}
