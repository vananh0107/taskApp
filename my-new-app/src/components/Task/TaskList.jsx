import React from 'react';
import { useDispatch } from 'react-redux';
import { updateTask, deleteTask } from '../../actions/taskActions';

const TaskList = ({ tasks, onEdit }) => {
  const dispatch = useDispatch();

  const handleToggleComplete = (task) => {
    dispatch(updateTask(task.id, { completed: !task.completed }));
  };

  const handleDelete = (taskId) => {
    if (window.confirm('Are you sure you want to delete this task?')) {
      dispatch(deleteTask(taskId));
    }
  };

  const getPriorityColor = (priority) => {
    switch (priority) {
      case 'High':
        return 'text-red-600';
      case 'Medium':
        return 'text-yellow-600';
      case 'Low':
        return 'text-green-600';
      default:
        return 'text-gray-600';
    }
  };

  const isOverdue = (dueDate, completed) =>
    dueDate && !completed && new Date(dueDate) < new Date();

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full bg-white shadow-md rounded">
        <thead>
          <tr className="bg-gray-200 text-gray-700 text-left">
            <th className="p-3">Title</th>
            <th className="p-3">Description</th>
            <th className="p-3">Priority</th>
            <th className="p-3">Due Date</th>
            <th className="p-3">Completed</th>
            <th className="p-3">Category</th>
            <th className="p-3">Assigned To</th>
            <th className="p-3">Actions</th>
          </tr>
        </thead>
        <tbody>
          {tasks.map((task) => {
            const overdue = isOverdue(task.dueDate, task.completed);
            return (
              <tr
                key={task.id}
                className={`${task.completed ? 'opacity-60' : ''} ${
                  overdue ? 'bg-red-50' : ''
                }`}
              >
                <td className="p-3 font-medium">{task.title}</td>
                <td className="p-3 text-sm">{task.description}</td>
                <td
                  className={`p-3 text-sm font-bold ${getPriorityColor(
                    task.priority
                  )}`}
                >
                  {task.priority}
                </td>
                <td
                  className={`p-3 text-sm ${
                    overdue ? 'text-red-500 font-bold' : 'text-gray-600'
                  }`}
                >
                  {new Date(task.dueDate).toLocaleString()}
                </td>
                <td className="p-3">
                  <input
                    type="checkbox"
                    checked={task.completed}
                    onChange={() => handleToggleComplete(task)}
                    className="form-checkbox h-5 w-5 text-blue-600"
                  />
                </td>
                <td className="p-3 text-sm text-gray-700">
                  {task.category?.name || 'N/A'}
                </td>
                <td className="p-3 text-sm text-gray-700">
                  {task.user?.firstName} {task.user?.lastName}
                </td>
                <td className="p-3 text-sm space-x-2">
                  <button
                    onClick={() => onEdit(task)}
                    className="text-blue-500 hover:text-blue-700"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleDelete(task.id)}
                    className="text-red-500 hover:text-red-700"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default TaskList;
