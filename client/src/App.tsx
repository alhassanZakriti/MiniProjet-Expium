import {useState, useEffect } from "react"
import React from "react"
import axios from "axios";

import { UserContext } from './contexts/UserContext'; // Path to your UserContext file

import {Routes, Route} from 'react-router-dom';
import SigninForm from './_auth/forms/SigninForm';
import { Home } from './_root/pages';
import SignupForm from './_auth/forms/SignupForm';
import AuthLayout from './_auth/AuthLayout';
import RootLayout from './_root/RootLayout';
import Activities from './_root/pages/Activities';
import Chat from './_root/pages/Chat';
import Friends from './_root/pages/Friends';
import Profile from './_root/pages/Profile';
import Preloader from "./components/Preloader";
import CreatePost from "./_root/pages/CreatePost";
import InsideChat from "./_root/pages/InsideChat";
import Settings from "./_root/pages/Settings";
import Search from "./_root/pages/Search";
import InsidePost from "./_root/pages/InsidePost";
import { set } from "react-hook-form";



export interface User {
  name: string;
  username: string;
  password: string;
  email: string;
  followers: number;
  following: number;
  
}



const App = () => {
  const [isLoading, setIsLoading] = useState(true);

  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    // Fetch user data using Axios
    axios.get("http://localhost:8080/user/user-info?username=ayoub ", )
      .then((response) => {
        setUser(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);
  console.log(user);

  
  React.useEffect(() => {
    // const User:{name: string, username: string, password: string, email: string, followers:number, following: number }= {
    //   name: "Al Hassan ZAKRITI",
    //   username: "alhassan.zakriti",
    //   password: "123456",
    //   email: "zakriti.alhassan1@gmail.com",
    //   followers: 122,
    //   following: 124,
    // };
  
    // setUser(User);
  
    // Simulate loading pages
    setTimeout(() => {
      setIsLoading(false);
    }, 2000);
  }, []);
  return (
    <UserContext.Provider value={{ user, setUser }}>
      <div>
        
        {isLoading && <Preloader />}
        <main>
          
          <Routes>

              {/*Public Routes */}

              <Route element={<AuthLayout />}>
                  <Route path='/sign-in' element={<SigninForm />} />
                  <Route path='/sign-up' element={<SignupForm />} />
              </Route>
              

              {/*Private Routes */}
              <Route element={<RootLayout />}>
                  <Route index element={<Home />} />
                  <Route path='/activities' element={<Activities />} />
                  <Route path='/add-post' element={<CreatePost />} />
                  <Route path='/chat' element={<Chat />} />
                  <Route path='/chat/message' element={<InsideChat />} />
                  <Route path='/friends' element={<Friends />} />
                  <Route path='/profile' element={<Profile />} />
                  <Route path='/settings' element={<Settings />} />
                  <Route path='/search' element={<Search />} />
                  <Route path='/post' element={<InsidePost />} />
              </Route>
              
          </Routes>
        </main>
      </div>
    </UserContext.Provider>
  )
}

export default App
