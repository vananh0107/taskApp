import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { updateTask, deleteTask } from '../../actions/taskActions';
import TaskForm from './TaskForm';

const TaskItem = ({ task }) => {
  const dispatch = useDispatch();
  const [isEditing, setIsEditing] = useState(false);

  const handleToggleComplete = () => {
    dispatch(updateTask(task.id, { completed: !task.completed }));
  };

  const handleDelete = () => {
    if (window.confirm('Are you sure you want to delete this task?')) {
      dispatch(deleteTask(task.id));
    }
  };

  const handleSaveEdit = async (updatedData) => {
    const result = await dispatch(updateTask(task.id, updatedData));
    if(result.success) {
      setIsEditing(false);
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

  const isOverdue = task.dueDate && !task.completed && new Date(task.dueDate) < new Date();

  return (
    <div className={`bg-white p-4 rounded-lg shadow mb-4 ${task.completed ? 'opacity-60' : ''} ${isOverdue ? 'border-l-4 border-red-500' : ''}`}>
      <div className="flex items-center justify-between mb-2">
        <h4 className={`text-lg font-semibold ${task.completed ? 'line-through' : ''}`}>{task.title}</h4>
        <div className="flex items-center space-x-2">
          <span className={`text-sm font-medium ${getPriorityColor(task.priority)}`}>
            {task.priority}
          </span>
          <input
            type="checkbox"
            checked={task.completed}
            onChange={handleToggleComplete}
            className="form-checkbox h-5 w-5 text-blue-600 rounded"
          />
        </div>
      </div>
      <p className={`text-gray-700 text-sm mb-2 ${task.completed ? 'line-through' : ''}`}>{task.description}</p>
      {task.category && (
        <p className="text-gray-500 text-xs mb-2">Category: {task.category.name}</p>
      )}
      {task.dueDate && (
        <p className={`text-gray-500 text-xs ${isOverdue ? 'text-red-500 font-bold' : ''}`}>
          Due: {new Date(task.dueDate).toLocaleString()}
        </p>
      )}
      <div className="flex justify-end space-x-2 mt-4">
        <button
          className="text-blue-500 hover:text-blue-700 text-sm"
          onClick={() => setIsEditing(true)}
          disabled={task.completed}
        >
          Edit
        </button>
        <button
          className="text-red-500 hover:text-red-700 text-sm"
          onClick={handleDelete}
        >
          Delete
        </button>
      </div>
      {isEditing && (
        <TaskForm
          task={task}
          onClose={() => setIsEditing(false)}
          onSave={handleSaveEdit}
        />
      )}
    </div>
  );
};

export default TaskItem;