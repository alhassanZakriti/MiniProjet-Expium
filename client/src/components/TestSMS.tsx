import React, { useState, useEffect } from 'react';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

interface ChatMessage {
  id: string;
  sender: string;
  recipient: string;
  content: string;
}

interface ChatComponentProps {
  currentUser: string;
}

const ChatComponent: React.FC<ChatComponentProps> = ({ currentUser }) => {
  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [messageInput, setMessageInput] = useState<string>('');

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws');
    const stomp = Stomp.over(socket);
    stomp.connect({}, () => {
      setStompClient(stomp);
    });
    return () => {
      if (stomp) stomp.disconnect();
    };
  }, []);

  useEffect(() => {
    let subscription: Stomp.Subscription | null = null;
    if (stompClient) {
      subscription = stompClient.subscribe(`/user/${currentUser}/queue/messages`, (response:any) => {
        const data: ChatMessage = JSON.parse(response.body);
        setMessages(prevMessages => [...prevMessages, data]);
      });
    }
    return () => {
      if (subscription) subscription.unsubscribe();
    };
  }, [currentUser, stompClient]);

  const sendMessage = () => {
    if (stompClient && messageInput.trim() !== '') {
      const message: ChatMessage = {
        id: 'uniqueId', // replace with a unique ID generator
        sender: currentUser,
        recipient: 'recipientId', // replace with the actual recipient's ID
        content: messageInput
      };
      stompClient.send('/app/chat', {}, JSON.stringify(message));
      setMessageInput('');
    }
  };

  return (
    <div>
      <h1>Chat Room</h1>
      <div>
        {messages.map((msg) => (
          <div key={msg.id}>
            <strong>{msg.sender}: </strong> {msg.content}
          </div>
        ))}
      </div>
      <input
        type="text"
        placeholder="Type your message..."
        value={messageInput}
        onChange={(e) => setMessageInput(e.target.value)}
      />
      <button onClick={sendMessage}>Send</button>
    </div>
  );
};

export default ChatComponent;