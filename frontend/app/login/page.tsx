"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";

export default function AuthPage() {
  const [isLogin, setIsLogin] = useState(true);
  const router = useRouter();

  return (
    <div style={container}>
      <div style={card}>

        {/* LEFT FORM */}
        <div style={left}>
          <h1 style={logo}><b>MyBank</b></h1>

          <h2>{isLogin ? "Login" : "Register"}</h2>

          <input placeholder="Email" style={input} />
          <input placeholder="Password" type="password" style={input} />

          {!isLogin && (
            <input placeholder="Confirm Password" type="password" style={input} />
          )}

          <button
            style={button}
            onClick={() => router.push("/dashboard")}
          >
            {isLogin ? "Login" : "Register"}
          </button>

          <p>
            {isLogin ? "New user?" : "Already have an account?"}{" "}
            <span style={link} onClick={() => setIsLogin(!isLogin)}>
              {isLogin ? "Register" : "Login"}
            </span>
          </p>
        </div>

        {/* RIGHT SIDE */}
        <div style={right}>
          <h2><b>Welcome to MyBank</b></h2>
          <p>Secure • Fast • Modern Banking</p>
        </div>

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
  display: "flex",
  width: "850px",
  height: "500px",
  borderRadius: "20px",
  overflow: "hidden",
  background: "rgba(255,255,255,0.08)",
  backdropFilter: "blur(20px)",
  boxShadow: "0 10px 40px rgba(0,0,0,0.3)"
};

const left = {
  flex: 1,
  display: "flex",
  flexDirection: "column" as const,
  justifyContent: "center",
  alignItems: "center",
  gap: "15px",
  color: "white",
  padding: "40px"
};

const right = {
  flex: 1,
  display: "flex",
  flexDirection: "column" as const,
  justifyContent: "center",
  alignItems: "center",
  color: "white",
  background: "linear-gradient(135deg,#4facfe,#00f2fe)"
};

const logo = {
  position: "absolute" as const,
  top: "30px",
  left: "40px"
};

const input = {
  padding: "12px",
  width: "250px",
  borderRadius: "8px",
  border: "none"
};

const button = {
  padding: "12px 20px",
  borderRadius: "8px",
  border: "none",
  background: "#00c6ff",
  cursor: "pointer"
};

const link = {
  color: "#00c6ff",
  cursor: "pointer"
};