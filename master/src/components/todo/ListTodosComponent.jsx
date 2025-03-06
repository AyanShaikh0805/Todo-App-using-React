import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "./security/AuthContext";
import { apiClient } from "./api/apiClient";


// ✅ Corrected API Call
export const retrieveAllTodosForUsernameApi = (username) => {
    return apiClient.get(`/users/${username}/todos`); // ✅ Corrected URL
};

// ✅ Add deleteTodo API function
export const deleteTodoApi = (username, id) => {
    return apiClient.delete(`/users/${username}/todos/${id}`);
};

function ListTodosComponent() {
    const authContext = useAuth();
    const username = authContext.username;
    const navigate = useNavigate();
    const [todos, setTodos] = useState([]);
    const [message, setMessage] = useState(null);

    // ✅ Fetch Todos on Component Mount
    
    useEffect(() => {
        if (username) {
            refreshTodos();
        }
    }, [username]);

    function refreshTodos() {
        
        retrieveAllTodosForUsernameApi(username)
            .then((response) => {
                setTodos(response.data);
            })
            .catch((error) => console.log("Error fetching todos:", error));
    }

    function deleteTodo(id) {
        deleteTodoApi(username, id)
            .then(() => {
                setMessage(`Deleted todo with id = ${id}`);
                refreshTodos();
            })
            .catch((error) => console.log("Error deleting todo:", error));
    }

    function updateTodo(id) {
        navigate(`/todo/${id}`);
    }

    function addNewTodo() {
        navigate(`/todo/-1`);
    }

    return (
        <div className="container">
            <h1>Things You Want To Do!</h1>
            {message && <div className="alert alert-warning">{message}</div>}
            <table className="table">
                <thead>
                    <tr>
                        <th>Description</th>
                        <th>Is Done?</th>
                        <th>Target Date</th>
                        <th>Delete</th>
                        <th>Update</th>
                    </tr>
                </thead>
                <tbody>
                    {todos.map((todo) => (
                        <tr key={todo.id}>
                            <td>{todo.description}</td>
                            <td>{todo.done.toString()}</td>
                            <td>{todo.targetDate.toString()}</td>
                            <td>
                                <button className="btn btn-warning" onClick={() => deleteTodo(todo.id)}>
                                    Delete
                                </button>
                            </td>
                            <td>
                                <button className="btn btn-success" onClick={() => updateTodo(todo.id)}>
                                    Update
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <button className="btn btn-success m-5" onClick={addNewTodo}>
                Add New Todo
            </button>
        </div>
    );
}

export default ListTodosComponent;
