"use client";

import { useEffect, useState } from "react";

export default function Profile() {

  const [email, setEmail] = useState("");

  useEffect(() => {
    const storedEmail = localStorage.getItem("email");
    if (storedEmail) setEmail(storedEmail);
  }, []);

  return (
    <div style={container}>
      <div style={card}>
        <h2>👤 Profile</h2>

        <p><b>Email:</b></p>
        <p style={{wordBreak:"break-all"}}>{email}</p>

        <p><b>Status:</b> Active</p>
      </div>
    </div>
  );
}

const container = {
  height: "100vh",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  background: "linear-gradient(135deg,#0f2027,#203a43,#2c5364)"
};

const card = {
  padding: "30px",
  borderRadius: "15px",
  background: "rgba(255,255,255,0.1)",
  color: "white",
  width: "300px"
};