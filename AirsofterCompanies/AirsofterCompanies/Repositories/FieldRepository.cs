using AirsofterCompanies.API;
using AirsofterCompanies.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Threading.Tasks;

namespace AirsofterCompanies.Repositories
{
    public class FieldRepository : IFieldRepository
    {
        private readonly ApiClient _apiClient;

        public FieldRepository()
        {
            _apiClient = new ApiClient();
        }

        public async Task<List<Field>> GetFieldsWithIdAndNameAsync(Guid companyId)
        {
            try
            {
                return await _apiClient.GetFieldsWithIdAndNameAsync(companyId);
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al obtener los campos: {ex.Message}");
            }
        }
    }
}
