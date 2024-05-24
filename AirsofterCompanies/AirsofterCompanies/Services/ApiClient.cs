using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using AirsofterCompanies.Models;
using Newtonsoft.Json;

namespace AirsofterCompanies.API
{
    public class ApiClient
    {
        private readonly HttpClient _httpClient;

        public ApiClient()
        {
            _httpClient = new HttpClient
            {
                BaseAddress = new Uri("https://airsofterapi20240521182128.azurewebsites.net/")
            };
        }

        public async Task<string> Login(string username, string password)
        {
            var data = new { Username = username, Password = password };
            var json = JsonConvert.SerializeObject(data);
            var content = new StringContent(json, System.Text.Encoding.UTF8, "application/json");

            content.Headers.ContentType = new MediaTypeHeaderValue("application/json");

            using (var response = await _httpClient.PostAsync("api/company/login", content))
            {
                if (response.IsSuccessStatusCode)
                {
                    var responseData = await response.Content.ReadAsStringAsync();
                    var admin = JsonConvert.DeserializeAnonymousType(responseData, new { Id = default(Guid), Username = default(string), CompanyName = default(string), Email = default(string) });
                    return JsonConvert.SerializeObject(admin);
                }
                else
                {
                    throw new Exception($"Error al iniciar sesión: {response.StatusCode}");
                }
            }
        }

        public async Task<List<Field>> GetFieldsWithIdAndNameAsync(Guid companyId)
        {
            try
            {
                var response = await _httpClient.GetAsync($"api/field/{companyId}");

                if (response.IsSuccessStatusCode)
                {
                    var responseData = await response.Content.ReadAsStringAsync();
                    var fields = JsonConvert.DeserializeObject<List<Field>>(responseData);
                    return fields;
                }
                else
                {
                    var errorMessage = await response.Content.ReadAsStringAsync();
                    throw new Exception($"Error al obtener los campos: {response.StatusCode} - {errorMessage}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al obtener los campos: {ex.Message}");
            }
        }

        public async Task<bool> SaveGameAsync(GameModel game)
        {
            try
            {
                var jsonContent = JsonConvert.SerializeObject(game);

                var content = new StringContent(jsonContent, Encoding.UTF8, "application/json");

                var response = await _httpClient.PostAsync("api/game/create", content);

                if (response.IsSuccessStatusCode)
                {
                    return true;
                }
                else
                {
                    var errorMessage = await response.Content.ReadAsStringAsync();
                    throw new Exception($"Error al guardar la partida: {response.StatusCode} - {errorMessage}");
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"Error al guardar la partida: {ex.Message}");
            }
        }
    }
}
