import { useNavigate } from 'react-router-dom';

export default function Logout({onLogout}) {
    const navigate = useNavigate();
    const handleLogout = () => {
        localStorage.removeItem('auth');
        onLogout();
        navigate('/login');
    }

    return (
        <button onClick = {handleLogout}>
            Logout
        </button>
    )
}