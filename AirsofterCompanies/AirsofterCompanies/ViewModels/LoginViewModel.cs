using System;
using System.Security;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;
using AirsofterCompanies.API;
using AirsofterCompanies.Models;
using AirsofterCompanies.Repositories;
using AirsofterCompanies.Views;
using Newtonsoft.Json;

namespace AirsofterCompanies.ViewModels
{
    public class LoginViewModel : ViewModelBase
    {
        private string _username;
        private SecureString _password;
        private string _errorMessage;
        private bool _isViewVisible = true;
        private readonly CompanyRepository companyRepository = new CompanyRepository();

        private readonly ApiClient _apiClient;

        public string Username
        {
            get => _username;
            set
            {
                _username = value;
                OnPropertyChanged(nameof(Username));
            }
        }

        public SecureString Password
        {
            get => _password;
            set
            {
                _password = value;
                OnPropertyChanged(nameof(Password));
            }
        }

        public string ErrorMessage
        {
            get => _errorMessage;
            set
            {
                _errorMessage = value;
                OnPropertyChanged(nameof(ErrorMessage));
            }
        }

        public bool IsViewVisible
        {
            get => _isViewVisible;
            set
            {
                _isViewVisible = value;
                OnPropertyChanged(nameof(IsViewVisible));
            }
        }

        public ICommand LoginCommand { get; }
        public ICommand RecoverPasswordCommand { get; }
        public ICommand ShowPasswordCommand { get; }
        public ICommand RememberPasswordCommand { get; }

        public LoginViewModel()
        {
            _apiClient = new ApiClient();
            LoginCommand = new ViewModelCommand(ExecuteLoginCommand, CanExecuteLoginCommand);
            RecoverPasswordCommand = new ViewModelCommand(p => ExecuteRecoverPassCommand("", ""));
        }

        private bool CanExecuteLoginCommand(object obj)
        {
            bool validData;
            if (string.IsNullOrWhiteSpace(Username) || Username.Length < 3 ||
                Password == null || Password.Length < 3)
                validData = false;
            else
                validData = true;
            return validData;
        }

        private async void ExecuteLoginCommand(object obj)
        {
            try
            {
                var responseData = await companyRepository.Login(Username, GetPasswordString(Password));
                Console.WriteLine(responseData);

                var user = JsonConvert.DeserializeObject<CompanyAccountModel>(responseData);
                if (user != null)
                {
                    Application.Current.Dispatcher.Invoke(() =>
                    {
                        AppContext.CurrentUser = user;
                        var mainView = new MainView();
                        var mainViewModel = new MainViewModel(user);
                        mainView.DataContext = mainViewModel;
                        mainView.Show();

                        Application.Current.MainWindow.Close();
                        Application.Current.MainWindow = mainView;
                    });

                    IsViewVisible = false;
                }
                else
                {
                    ErrorMessage = "Invalid user data received.";
                }
            }
            catch (Exception ex)
            {
                ErrorMessage = $"Error al iniciar sesión: {ex.Message}";
            }
        }






        private void ExecuteRecoverPassCommand(string username, string email)
        {
            throw new NotImplementedException();
        }

        private string GetPasswordString(SecureString securePassword)
        {
            IntPtr ptr = System.Runtime.InteropServices.Marshal.SecureStringToBSTR(securePassword);
            try
            {
                return System.Runtime.InteropServices.Marshal.PtrToStringBSTR(ptr);
            }
            finally
            {
                System.Runtime.InteropServices.Marshal.ZeroFreeBSTR(ptr);
            }
        }
    }
}
