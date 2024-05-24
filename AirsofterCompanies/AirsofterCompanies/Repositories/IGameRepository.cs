using AirsofterCompanies.Models;
using System.Threading.Tasks;

namespace AirsofterCompanies.Repositories
{
    public interface IGameRepository
    {
        Task<bool> SaveGameAsync(GameModel game);
    }
}
