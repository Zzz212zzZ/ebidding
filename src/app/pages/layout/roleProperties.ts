export interface MenuItem {
  path: string,
  desc: string,
  icon: string
}

export const ClientMenu: MenuItem[] = [
  {
    path: '/layout/client/homepage',
    desc: 'Client Dashboard',
    icon: 'dashboard'
  },
  {
    path: '/layout/client/bidding',
    desc: 'Bidding',
    icon: 'paid'
  },
  {
    path: '/layout/client/history',
    desc: 'History',
    icon: 'history'
  }
];
  
export const TraderMenu: MenuItem[] = [
  {
    path: '/layout/trader/homepage',
    desc: 'Manager Dashboard',
    icon: 'dashboard'
  },
  {
    path: '/layout/trader/admin',
    desc: 'Admin',
    icon: 'manage_accounts'
  },
  {
    path: '/layout/trader/bond',
    desc: 'Bond',
    icon: 'local_atm'
  }
];


export const avatarUrl = {
  trader: 'https://s2.loli.net/2023/07/08/TpcRn5ArWPdmQVK.png',
  client: 'https://s2.loli.net/2023/07/08/Jk9seHrTn2ACuQW.png'

}