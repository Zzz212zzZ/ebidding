export class DataItem {
    bwic_id!: bigint;
    bond_id!: string;
    size!: number;
    start_price!: number;
    present_price!: number;
    start_time!: Date;
    due_time!: Date;
    last_bid_time!: Date;
    bid_counts!: bigint;
  }
  