"use client";

import { useEffect, useState } from "react";

export default function Dashboard() {
  const [balance, setBalance] = useState(0);
  const [transactions, setTransactions] = useState<any[]>([]);
  const userId = localStorage.getItem("userId");
  const userName = localStorage.getItem("userName") || "User";

  useEffect(() => {
    if (!userId) return;

    fetch(`http://localhost:8080/balance?id=${userId}`)
      .then(res => res.text())
      .then(data => setBalance(Number(data)));

    fetch(`http://localhost:8080/transactions?id=${userId}`)
      .then(res => res.json())
      .then(data => setTransactions(data));
  }, []);

  return (
    <div style={{ display: "flex", height: "100vh", fontFamily: "sans-serif" }}>

      {/* SIDEBAR */}
      <div style={sidebar}>
        <h2 style={{ marginBottom: "50px" }}>MyBank</h2>

        <SidebarItem label="Dashboard" />
        <SidebarItem label="Transactions" />
        <SidebarItem label="Accounts" />
        <SidebarItem label="Settings" />
        <SidebarItem label="Profile" />
      </div>

      {/* MAIN */}
      <div style={main}>

        {/* TOP BAR */}
        <div style={topbar}>
          <input placeholder="Search..." style={search} />
          <div style={profile}> {userName}</div>
        </div>

        {/* CARDS */}
        <div style={cardsRow}>
          {/* CREDIT CARD */}
          <div style={creditCard}>
            <p style={label}>CARD NUMBER</p>
            <h2 style={cardNumber}>XXXX XXXX XXX9 5025</h2>

            <div style={{ display: "flex", justifyContent: "space-between", marginTop: "20px" }}>
              <div>
                <p style={label}>CARD HOLDER</p>
                <p>NAME</p>
              </div>

              <div>
                <p style={label}>VALID THRU</p>
                <p>09/27</p>
              </div>
            </div>
          </div>

          {/* SAVINGS */}
          <div style={savingCard}>
            <h3>Your Saving Status</h3>
            <h2>4.28%</h2>
            <p>Your spending increased</p>
          </div>

          {/* BALANCE */}
          <div style={balanceCard}>
            <h3>Your Working Balance</h3>
            <h1>₹ {balance}</h1>
            <p>Saving Account</p>
          </div>
        </div>

        {/* TRANSACTIONS */}
        <div style={tableContainer}>
          <h3>Latest Transactions</h3>

          <table style={table}>
            <thead>
              <tr>
                <th>Type</th>
                <th>Amount</th>
              </tr>
            </thead>

            <tbody>
              {transactions.map((t, i) => (
                <tr key={i}>
                  <td style={{ color: t.type === "CREDIT" ? "green" : "red" }}>
                    {t.type}
                  </td>
                  <td>₹ {t.amount}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

      </div>
    </div>
  );
}

/* COMPONENTS */

function SidebarItem({ label }: { label: string }) {
  return (
    <div style={sidebarItem}>
      {label}
    </div>
  );
}

/* STYLES */

const sidebar = {
  width: "220px",
  background: "linear-gradient(180deg, #0f2027, #203a43)",
  color: "white",
  padding: "20px"
};

const sidebarItem = {
  margin: "15px 0",
  cursor: "pointer",
  fontSize: "15px",
  opacity: 0.9
};

const main = {
  flex: 1,
  background: "#f5f7fa", // 🔥 NOT PURE WHITE
  padding: "20px",
  color: "#111" // 🔥 FIX VISIBILITY
};

const topbar = {
  display: "flex",
  justifyContent: "space-between",
  marginBottom: "20px"
};

const search = {
  padding: "10px",
  borderRadius: "10px",
  border: "1px solid #ccc",
  width: "250px"
};

const profile = {
  background: "#4facfe",
  color: "white",
  padding: "10px 15px",
  borderRadius: "20px"
};

const cardsRow = {
  display: "flex",
  gap: "20px",
  marginBottom: "30px"
};

const creditCard = {
  flex: 1,
  padding: "20px",
  borderRadius: "20px",
  background: "linear-gradient(135deg, #d4fc79, #96e6a1)",
  color: "#111",
  boxShadow: "0 10px 30px rgba(0,0,0,0.2)"
};

const savingCard = {
  flex: 1,
  padding: "20px",
  borderRadius: "20px",
  background: "#ff7e5f",
  color: "white"
};

const balanceCard = {
  flex: 1,
  padding: "20px",
  borderRadius: "20px",
  background: "#00c6ff",
  color: "#111"
};

const label = {
  fontSize: "12px",
  opacity: 0.7
};

const cardNumber = {
  letterSpacing: "3px",
  fontSize: "20px",
  fontWeight: "600"
};

const tableContainer = {
  background: "white",
  padding: "20px",
  borderRadius: "20px",
  boxShadow: "0 5px 20px rgba(0,0,0,0.1)"
};

const table = {
  width: "100%",
  marginTop: "10px",
  borderCollapse: "collapse" as const
};