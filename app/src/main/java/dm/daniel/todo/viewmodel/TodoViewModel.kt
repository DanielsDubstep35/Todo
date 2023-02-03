package dm.daniel.todo.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.daniel.todo.model.Todo
import dm.daniel.todo.model.TodosApi
import kotlinx.coroutines.launch

sealed interface TodoUIState {
    data class Success(val todos: List<Todo>): TodoUIState
    object Error: TodoUIState
    object Loading: TodoUIState
}

class TodoViewModel: ViewModel() {
    var todoUIState: TodoUIState by mutableStateOf(TodoUIState.Loading)
        private set

    init {
        getTodosList()
    }

    private fun getTodosList() {
        viewModelScope.launch {
            val todosApi: TodosApi?
            try {
                todosApi = TodosApi.getInstance()
                todoUIState = TodoUIState.Success(todosApi.getTodos())
            } catch (e: Exception) {
                Log.d("VIEWMODEL", e.message.toString())
                todoUIState = TodoUIState.Error
            }
        }
    }
}