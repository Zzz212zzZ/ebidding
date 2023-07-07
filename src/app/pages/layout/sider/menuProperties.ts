


// client-menu.ts
export const ClientMenu = [
    {
      title: 'Client Dashboard',
      icon: 'dashboard',
      children: [
        {
          path: '/layout/client/homepage',
          desc: 'Homepage'
        }
      ]
    },
    {
      title: 'Client Transaction',
      icon: 'pie-chart',
      children: [
        {
          path: '/layout/client/bidding',
          desc: 'Bidding'
        },
        {
          path: '/layout/client/history',
          desc: 'History'
        },
      ]
    }
  ];
  
  // trader-menu.ts
  export const TraderMenu = [
    {
      title: 'Maneger Dashboard',
      icon: 'dashboard',
      children: [
        {
          path: '/layout/trader/homepage',
          desc: 'Homepage'
        }
      ]
    },
    {
      title: 'Manager Workplace',
      icon: 'pie-chart',
      children: [
        {
          path: '/layout/trader/admin',
          desc: 'Admin'
        },
        {
          path: '/layout/trader/bond',
          desc: 'Bond'
        },
      ]
    }
  ];
  