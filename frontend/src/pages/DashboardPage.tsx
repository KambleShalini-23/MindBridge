import { useState } from "react";
import { getPatientData } from "../services/protectedService";

function DashboardPage() {
  const [message, setMessage] = useState("");

  async function handleGetPatientData() {
    try {
      const data = await getPatientData();
      setMessage(data);
    } catch (error) {
      setMessage("Failed to fetch protected data");
    }
  }

  return (
    <div>
      <h1>Dashboard Page</h1>

      <button onClick={handleGetPatientData}>
        Get Protected Patient Data
      </button>

      <p>{message}</p>
    </div>
  );
}

export default DashboardPage;