using AirsofterCompanies.Models;
using AirsofterCompanies.Repositories;
using GalaSoft.MvvmLight.CommandWpf;
using System;
using System.Collections.ObjectModel;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;

namespace AirsofterCompanies.ViewModels
{
    public class CreateGameViewModel : ViewModelBase
    {
        CompanyAccountModel currentUser = AppContext.CurrentUser;
        private readonly IGameRepository _gameRepository;
        private IFieldRepository _fieldRepository;
        private ObservableCollection<Field> _fields = new ObservableCollection<Field>();

        public string Description { get; set; }

        private DateTime _gameDate;

        public DateTime GameDate
        {
            get { return _gameDate; }
            set
            {
                _gameDate = value;
                OnPropertyChanged(nameof(GameDate));
            }
        }
        public int MaxPlayers { get; set; }
        public Field SelectedField { get; set; }
        public ICommand SaveGameCommand { get; private set; }


        public ObservableCollection<Field> Fields
        {
            get => _fields;
            set
            {
                _fields = value;
                OnPropertyChanged(nameof(Fields));
            }
        }

        public IFieldRepository FieldRepository
        {
            get => _fieldRepository;
            set
            {
                _fieldRepository = value;
                LoadFields();
            }
        }

        public CreateGameViewModel()
        {
            _gameRepository = new GameRepository(); 
            SaveGameCommand = new ViewModelCommand(CreateGame, CanSaveGame);
            GameDate = DateTime.Now;

            Fields = new ObservableCollection<Field>();
        }

        private bool CanSaveGame(object obj)
        {
            return true;
        }

        private async void CreateGame(object obj)
        {
            await SaveGameAsync();
        }
        private async Task SaveGameAsync()
        {
            try
            {
                var newGame = new GameModel
                {
                    Description = Description,
                    GameDate = GameDate,
                    MaxPlayers = MaxPlayers,
                    FieldId = SelectedField?.Id
                };

                await _gameRepository.SaveGameAsync(newGame);

                MessageBox.Show("¡El juego se ha creado exitosamente!", "Éxito", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al crear el juego: {ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private async void LoadFields()
        {
            if (FieldRepository == null || currentUser == null)
                return;

            try
            {
                var fields = await FieldRepository.GetFieldsWithIdAndNameAsync(currentUser.Id);

                Fields.Clear(); 

                foreach (var field in fields)
                {
                    Fields.Add(field);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al obtener los campos: {ex.Message}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }
    }
}
