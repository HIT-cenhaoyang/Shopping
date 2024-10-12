import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';

export default function Login({onLogin}) {
    const [userName, setUserName] = useState('');
    const [password, setPassword] =useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
    
        const loginData = {
            userName: userName,
            password: password
        }

        fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {
            'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        })
            .then((response) => {
            if (!response.ok) {
                throw new Error('Invalid credentials');
            }
            return response.text();
            })
            .then((data) => {
            localStorage.setItem('auth', 'true');
            onLogin();
            navigate('/orders');
            })
            .catch((error) => {
            setError(error.message);
            });
    }
    return (
        <div>
            <h2>Login</h2>
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <form onSubmit={handleLogin}>
          <div>
            <label>Username:</label>
            <input 
              type="text" 
              value={userName} 
              onChange={(input) => setUserName(input.target.value)} 
            />
          </div>
          <div>
            <label>Password:</label>
            <input 
              type="password" 
              value={password} 
              onChange={(input) => setPassword(input.target.value)} 
            />
          </div>
          <button type="submit">Login</button>
        </form>
      </div>
    )
}