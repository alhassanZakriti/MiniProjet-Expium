import React, { createContext, useState, useEffect } from 'react';
// import { User } from '../types'; // Add the import statement for the User type

interface User {
    name: string;
    username: string;
    email: string;
    password: string;
    followers: number;
    following: number;
  }

interface UserContextType {
    user: User | null;
    setUser: React.Dispatch<React.SetStateAction<User | null>>;
}

export const UserContext = createContext<UserContextType>({
    user: null,
    setUser: () => {},
});

