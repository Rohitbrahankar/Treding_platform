import React from 'react';
import {
  Sheet,
  SheetTrigger,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetDescription,
} from '@/components/ui/sheet';
import { Avatar, AvatarImage } from '@/components/ui/avatar';
import { Search } from 'lucide-react';
import Sidebar from './Sidebar';

const Navbar = () => {
  return (
 <div className="flex items-center justify-between p-4 bg-gray-800 text-white">
  {/* LEFT SIDE GROUP: Menu + Dhan Trading + Search */}
  <div className="flex items-center gap-6">
    {/* Menu Button */}
    <Sheet>
      <SheetTrigger className="px-3 py-2 text-sm font-medium text-white bg-blue-700 rounded-lg hover:bg-blue-800">
        Menu
      </SheetTrigger>
      <SheetContent side="left" className="w-60 bg-green-200 text-white">
        <SheetHeader className="text-black">
          <SheetTitle>
            <div className="text-3xl flex flex-col items-center gap-2">
              <Avatar>
                <AvatarImage
                  className="left-9 flex-auto"
                  src="https://media.istockphoto.com/id/1822381013/photo/speech-bubble-with-financial-chart-icon-on-the-podium.jpg"
                />
              </Avatar>
              <div className="text-center">
                <span className="text-2xl font-bold">Dhan</span>
                <span className="text-2xl font-bold">Trading</span>
              </div>
            </div>
          </SheetTitle>
          <SheetDescription>
            <Sidebar />
          </SheetDescription>
        </SheetHeader>
      </SheetContent>
    </Sheet>

    {/* Dhan Trading Text */}
    <p className="text-lg font-semibold text-white">Dhan Trading</p>

    {/* Search Button */}
    <button  className="flex items-center gap-2 bg-white text-black px-3 py-1 rounded-md">
      <Search size={18} />
      <span>Search</span>
    </button>
  </div>

  {/* RIGHT SIDE: Profile Avatar */}
  <div>
    <Avatar>
      <AvatarImage
        className="rounded-full w-10 h-10"
        src="https://i.pravatar.cc/100"
        alt="profile"
      />
    </Avatar>
  </div>
</div>



  );
};

export default Navbar;
