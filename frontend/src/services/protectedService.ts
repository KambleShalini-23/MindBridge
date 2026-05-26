import axiosClient from "../api/axiosClient";

export async function getPatientData(): Promise<string> {
  const response = await axiosClient.get<string>("/patient");
  return response.data;
}