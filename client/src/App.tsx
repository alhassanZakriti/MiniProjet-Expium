
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


const App = () => {
  return (
    <div>
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
                <Route path='/chat' element={<Chat />} />
                <Route path='/friends' element={<Friends />} />
                <Route path='/profile' element={<Profile />} />
            </Route>
            
        </Routes>
      </main>
    </div>
  )
}

export default App
