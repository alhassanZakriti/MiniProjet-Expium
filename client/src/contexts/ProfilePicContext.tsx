import React, { createContext, useState, useEffect } from 'react';
// import { User } from '../types'; // Add the import statement for the User type

// interface User {
//     name: string;
//     username: string;
//     email: string;
//     password: string;
//     followers: number;
//     following: number;
// }

interface ProfilePicContextType {
    profilePic: string | null;
    setProfilePic: React.Dispatch<React.SetStateAction<string | null>>;
}

export const ProfilePicContext = createContext<ProfilePicContextType>({
    profilePic: null,
    setProfilePic: () => {},
});

