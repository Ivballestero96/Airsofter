using AirsofterCompanies.API;
using AirsofterCompanies.Models;
using Newtonsoft.Json;
using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace AirsofterCompanies.Repositories
{
    public class GameRepository : IGameRepository
    {
        private readonly ApiClient _apiClient;

        public GameRepository()
        {
            _apiClient = new ApiClient();
        }

        public async Task<bool>SaveGameAsync(GameModel gameModel)
        {
            return await _apiClient.SaveGameAsync(gameModel);
        }
    }
}
