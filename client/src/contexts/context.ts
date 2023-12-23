import { createContext } from "react";
import { User } from "../App";
export const UsersContext = createContext<User | undefined >(undefined);