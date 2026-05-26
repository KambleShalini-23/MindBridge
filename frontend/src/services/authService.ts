import axiosClient from '../api/axiosClient';

export type LoginRequest = {
    emailId : string;
    password : string;
}

export type LoginResponse ={
    token : string;
    refreshToken : string;
}

export async function loginUser(data: LoginRequest) : Promise<LoginResponse> {
    const response = await axiosClient.post<LoginResponse>("users/login",data);
    return response.data}