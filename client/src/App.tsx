import {useState } from "react"
import React from "react"
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



const App = () => {
  const [isLoading, setIsLoading] = useState(true);

  React.useEffect(() => {
    // Simulate loading pages
    setTimeout(() => {
      setIsLoading(false);
    }, 2000);
  }, []);
  return (
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
                <Route path='/post' element={<CreatePost />} />
                <Route path='/chat' element={<Chat />} />
                <Route path='/chat/message' element={<InsideChat />} />
                <Route path='/friends' element={<Friends />} />
                <Route path='/profile' element={<Profile />} />
                <Route path='/settings' element={<Settings />} />
            </Route>
            
        </Routes>
      </main>
    </div>
  )
}

export default App
