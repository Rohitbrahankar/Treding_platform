import {
  Table,
  TableBody,
  TableCell,
  TableRow
} from "@/components/ui/table"; // adjust the path as needed
import {
  Avatar,
  AvatarImage
} from "@/components/ui/avatar"; // adjust the path if needed
import {
  TableHeader,
  TableHead
} from "@/components/ui/table"; // 

const AssetTable=()=> {
  return (
    <div className="p-4">
      <Table>
        <TableHeader>
    <TableRow>
      <TableHead >Coins</TableHead>
       <TableHead >SYMBOL</TableHead>
      <TableHead > VOLUME </TableHead>
      <TableHead >MARKET CAP</TableHead>
      <TableHead >24 hr</TableHead>
      <TableHead >PRICE</TableHead>
    </TableRow>
  </TableHeader>
        <TableBody>
          {[1, 1, 1, 1, 1, 1,1,1].map((item, index) => (
            <TableRow key={index}>
              <TableCell className="font-medium flex items-center gap-2">
                <Avatar>
                  <AvatarImage src="https://media.istockphoto.com/id/502347558/photo/bitcoin-on-white.jpg?s=612x612&w=0&k=20&c=s8Q2mGv9rq8-lLhq33SzK4RPl5n5wYUwr_66ZBDA6b4=" />
                </Avatar>
                <span>BitCoin</span>
              </TableCell>
              <TableCell>BTC</TableCell>
              <TableCell>25,866,706,828</TableCell>
              <TableCell>2,087,549,297,958</TableCell>
              <TableCell>-0.07062%</TableCell>
              <TableCell>$105,003</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}

export default AssetTable;
