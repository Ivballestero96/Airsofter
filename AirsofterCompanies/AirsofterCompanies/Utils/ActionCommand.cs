using System;
using System.Windows.Input;

public class ActionCommand : ICommand
{
    private readonly Action _action;

    public ActionCommand(Action action)
    {
        _action = action ?? throw new ArgumentNullException(nameof(action));
    }

    public event EventHandler CanExecuteChanged;

    public bool CanExecute(object parameter)
    {
        return true; 
    }

    public void Execute(object parameter)
    {
        _action();
    }
}
