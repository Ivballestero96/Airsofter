using AirsofterCompanies.Models;
using AirsofterCompanies.Repositories;
using AirsofterCompanies.Views;
using GalaSoft.MvvmLight.CommandWpf;
using System;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Input;

namespace AirsofterCompanies.ViewModels
{
    public class MainViewModel : ViewModelBase
    {
        private CompanyAccountModel _currentCompanyAccount;
        private ICompanyRepository companyRepository;
        public ICommand OpenCreateGameCommand { get; private set; }

        public CompanyAccountModel CurrentCompanyAccount
        {
            get => _currentCompanyAccount;
            set
            {
                _currentCompanyAccount = value;
                OnPropertyChanged(nameof(CurrentCompanyAccount));
            }
        }

        public MainViewModel(CompanyAccountModel user)
        {
            companyRepository = new CompanyRepository();
            CurrentCompanyAccount = user;

            OpenCreateGameCommand = new RelayCommand(OpenCreateGame);

        }

        public MainViewModel() : this(new CompanyAccountModel())
        {
            LoadCurrentUserData();
        }

        private void OpenCreateGame()
        {
            try
            {
                var createGameWindow = new CreateGameWindow();
                createGameWindow.ShowDialog();
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error al abrir la ventana de creación de juegos: {ex.Message}");
            }
        }

        private void LoadCurrentUserData()
        {
            try
            {
                string username = Thread.CurrentPrincipal.Identity.Name;
                var user = companyRepository.GetByUsername(username).Result;

                if (user != null)
                {
                    CurrentCompanyAccount.Username = user.Username;
                    CurrentCompanyAccount.CompanyName = $"Welcome {user.CompanyName};)";
                }
                else
                {
                    CurrentCompanyAccount.CompanyName = "Invalid user, not logged in";
                }
            }
            catch (Exception ex)
            {
                CurrentCompanyAccount.CompanyName = $"Error loading user data: {ex.Message}";
            }
        }
    }
}
