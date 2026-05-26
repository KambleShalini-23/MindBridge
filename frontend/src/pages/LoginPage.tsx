import {useState} from 'react';
import {loginUser} from '../services/authService';

function LoginPage(){
    const [emailId, setEmailId] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    async function handleLogin(event: React.FormEvent) {
        event.preventDefault();
        try{
            const response = await loginUser({
                emailId,password});

            localStorage.setItem("token", response.token);
            localStorage.setItem("refreshToken", response.refreshToken);
            setMessage("Login successful!");
        }
        catch(error){
            console.log(error)

            if (error.response) {
                console.log("Response data:", error.response.data);
                console.log("Status:", error.response.status);
              }

            setMessage("Login failed. Please check your credentials.");
        }
    }


    return(
        <div>
        <h1>Login Page</h1>
        <form onSubmit={handleLogin}>
            <div>
                <label>Email ID:</label>
                <input
                    type="email"
                    value={emailId}
                    onChange={(e) => setEmailId(e.target.value)}
                />
            </div>
            <div>
                <label>Password:</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>
            <button type="submit">Login</button>
        </form>
        <p>{message}</p>
        </div>
        );
}

export default LoginPage;

// function LoginPage(){
//     return <h1> Login Page </h1>;
// }
//
// export default LoginPage;