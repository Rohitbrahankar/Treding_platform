// d
// import { SheetClose } from '@/components/ui/sheet';
// import { HomeIcon, icons } from 'lucide-react';
// import path from 'path';
// import React from 'react';
// const menu=[
// {name:'Home',path:'/', icons:< HomeIcon className='h-6 w-6'/>},
// {name:'Home',path:'/', icons:< HomeIcon className='h-6 w-6'/>},

// {name:'Home',path:'/', icons:< HomeIcon className='h-6 w-6'/>},

// {name:'Home',path:'/', icons:< HomeIcon className='h-6 w-6'/>},



// ];

// const sidebar = () => {
//    return (
//     <div className='mt-10 space-y-5'>
//         <div>
//         <SheetClose className='w-full'>
//                    <button  varient="outline" className='flex items-center gap-5 py-6 w-full text-white' >
                
//                 <span className='w-8'>
//                     <HomeIcon/>
//                 </span> 
//                 <p>Home</p>
                     
//             </button>  



//         </SheetClose>
            
//         </div>

//     </div>
//    )
// }
// export default sidebar;

import React from 'react';
import {
  HomeIcon,
  BarChartIcon,
  SettingsIcon,
  UserIcon,
} from 'lucide-react';
import { SheetClose } from '@/components/ui/sheet';

const menu = [
  { name: 'Home', path: '/', icon: HomeIcon },
  { name: 'Dashboard', path: '/dashboard', icon: BarChartIcon },
  { name: 'Settings', path: '/settings', icon: SettingsIcon },
  { name: 'Profile', path: '/profile', icon: UserIcon },
];

const Sidebar = () => {
  return (
    <div className="mt-10 space-y-2 px-2">
      {menu.map((item, index) => {
        const Icon = item.icon;
        return (
            <SheetClose className='w-full' >

           
          <button
            key={index}
            className="flex items-center gap-4 py-2 px-4 w-full text-left text-black hover:bg-white/20 rounded transition"
          >
            <Icon className="w-5 h-5" />
            <span className="font-medium">{item.name}</span>
          </button>
           </SheetClose>
        );
      })}
    </div>
  );
};

export default Sidebar;






