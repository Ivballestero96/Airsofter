using AirsofterCompanies.ViewModels;

namespace AirsofterCompanies
{
    public static class ViewModelLocator
    {
        private static MainViewModel _mainViewModel;
        private static CreateGameViewModel _createGameViewModel;

        public static MainViewModel MainViewModelInstance
        {
            get
            {
                if (_mainViewModel == null)
                {
                    _mainViewModel = new MainViewModel();
                }
                return _mainViewModel;
            }
        }

        public static CreateGameViewModel CreateGameViewModelInstance
        {
            get
            {
                if (_createGameViewModel == null)
                {
                    _createGameViewModel = new CreateGameViewModel();
                }
                return _createGameViewModel;
            }
        }
    }
}
